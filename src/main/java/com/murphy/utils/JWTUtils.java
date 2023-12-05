package com.murphy.utils;

import com.murphy.model.entity.sys.SysUser;
import com.murphy.model.vo.LoginUserVO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

/**
 * @author admin
 */
@Slf4j
public class JWTUtils {


    /**
     * 盐
     */
    private static final String SALT_KEY = "murphy";

    /**
     * 令牌有效期毫秒
     */
    private static final long TOKEN_VALIDITY = 86400000; //一天
//    private static final long TOKEN_VALIDITY = 60000;


    /**
     * Base64 密钥
     */
    private final static String SECRET_KEY =  Base64.getEncoder().encodeToString(SALT_KEY.getBytes(StandardCharsets.UTF_8));



    private static boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            log.error("无效的token：" + authToken);
        }
        return false;
    }


    /**
     * 获取token
     * @param u user
     * @return token
     */
    public static String createToken(SysUser u) {
        Date validity = new Date((new Date()).getTime() + TOKEN_VALIDITY);
        return Jwts.builder()
                // 代表这个JWT的主体，即它的所有人
                .setSubject(String.valueOf(u.getId()))
                // 代表这个JWT的签发主体
                .setIssuer("")
                // 是一个时间戳，代表这个JWT的签发时间；
                .setIssuedAt(new Date())
                .claim("userId", u.getId())
                .claim("username", u.getUsername())
                .claim("realname", u.getNickname())
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setExpiration(validity)
                .compact();
    }

    /**
     * 验证token合法性 成功返回token
     */
    public static LoginUserVO verify(String token) throws Exception {
        if (validateToken(token)) {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
            String audience = claims.getAudience();
            String userId = claims.get("userId", String.class);
            String username = claims.get("username", String.class);
            String nickname = claims.get("nickname", String.class);
            LoginUserVO loginUserVO = new LoginUserVO().setId(userId).setUsername(username).setNickname(nickname).setValid(true);
            log.info("===token有效{},客户端{}", loginUserVO, audience);
            return loginUserVO;
        }
        log.error("***token无效***");
        return new LoginUserVO();
    }


    public static void main(String[] args) throws Exception {
        SysUser user = new SysUser();
        user.setId("0").setUsername("admin").setNickname("abc");
        String token = createToken(user);
        System.err.println(token);

        LoginUserVO verify = verify(token);
        System.err.println(verify);


        String token1 = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJudWxsIiwiaXNzIjoiIiwiaWF0IjoxNzAxMzI2NzU5LCJ1c2VybmFtZSI6ImFkbWluIiwiZXhwIjoxNzAxNDEzMTU5fQ.FT40vG0ksjkTuK9fdpHKjxOeO0ABpnvHmbdfn4MEzLujCOPvOufVAwPKtSueHmuxvy0nA_45r8OBzUm-YB2M2Q";
        System.err.println("123=>" + verify(token1));
    }
}
