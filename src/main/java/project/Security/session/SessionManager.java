package project.Security.session;
import org.springframework.stereotype.Component;

import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private static final String SESSIONNAME="mysessionname2";
    private Map<String,Object> sessionStore = new ConcurrentHashMap<>();

    /* 세션 생성 */
    public void createSession(Object value, HttpServletResponse response){
        String uid= UUID.randomUUID().toString();
        sessionStore.put(uid,value);////////
        Cookie cookie=new Cookie(SESSIONNAME,uid);
        response.addCookie(cookie);
    }
    /* 세션 조회 */

    public Object getSession(HttpServletRequest request)
    {
        Cookie sessionCookie = findCookie(request,SESSIONNAME);
        if(sessionCookie==null){
            return null;
        }
        return sessionCookie;
    }

    public Cookie findCookie(HttpServletRequest request, String  cookieName)
    {
        if(request.getCookies()==null){return null;}

        return Arrays.stream(request.getCookies()).filter(x->x.getName().equals(cookieName))
                .findFirst().orElse(null);
    }

    /*세션 만료*/
    public void expireSession(HttpServletRequest request)
    {
        Cookie sessionCookie= findCookie(request,SESSIONNAME);
        if(request.getCookies()!=null){
            sessionStore.remove(sessionCookie.getValue());///////
        }

    }

}
