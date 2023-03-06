/**
 * @author hbc
 * @version 1.0
 * @description 描述
 * @date 2023/3/6 14:03
 */
@javax.servlet.annotation.WebFilter(filterName = "TestFilter")
public class TestFilter implements javax.servlet.Filter {
    public void destroy() {
    }

    public void doFilter(javax.servlet.ServletRequest req, javax.servlet.ServletResponse resp, javax.servlet.FilterChain chain) throws javax.servlet.ServletException, java.io.IOException {
        chain.doFilter(req, resp);
    }

    public void init(javax.servlet.FilterConfig config) throws javax.servlet.ServletException {

    }

}
