/**********************************
 *@项目名称: api-parent
 *@文件名称: io.bhex.broker.filter
 *@Date 2018/9/3
 *@Author peiwei.ren@bhex.io 
 *@Copyright（C）: 2018 BlueHelix Inc.   All rights reserved.
 *注意：本内容仅限于内部传阅，禁止外泄以及用于其他的商业目的。
 ***************************************/
package io.bhex.broker.core.filter;


import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

@Deprecated
@Slf4j
public class DomainFilter implements Filter {

    /**
     * contain: /api/ /m/api/ /mapi/ /s_api/ /m/s_api/ /ms_api/
     */
    private static final Pattern URL_PREFIX_PATTERN = Pattern.compile("^/(m/|m|s_|m/s_|ms_)?api/");

    private static final Pattern APP_REQUEST_URL_PREFIX = Pattern.compile("^/(m/|m|m/s_|ms_)api/");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        String originalUri = request.getRequestURI();
//        Matcher matcher = URL_PREFIX_PATTERN.matcher(originalUri);
//        if (matcher.find()) {
//            log.debug("request uri:({}) forward to:({})", originalUri, matcher.replaceFirst("/"));
//            request.setAttribute(BrokerCoreConstants.ORIGINAL_REQUEST_URI_ATTR, originalUri);
//            request.getRequestDispatcher(matcher.replaceFirst("/")).forward(request, response);
//            return;
//        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
