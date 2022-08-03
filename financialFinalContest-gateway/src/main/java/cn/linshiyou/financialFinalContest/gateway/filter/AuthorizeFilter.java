package cn.linshiyou.financialFinalContest.gateway.filter;

import cn.linshiyou.financialFinalContest.gateway.util.JwtHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author: LJ
 * @Description: 鉴权过滤器
 */
@Component
@Order(value = 0)
public class AuthorizeFilter implements GlobalFilter {

    /**
     * 信息头
     */
    private static final String AUTHORIZE_TOKEN = "token";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();

        ServerHttpResponse response = exchange.getResponse();
        //如果是登录或短信服务请求则放行
        String path = request.getURI().getPath();
        if (path.contains("/user/login") || path.startsWith("/wx")) {
            return chain.filter(exchange);
        }
        HttpHeaders headers = request.getHeaders();
        String token = headers.getFirst(AUTHORIZE_TOKEN);


        if (StringUtils.isEmpty(token)) {
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        try {
            JwtHelper.parseJWT(token);
        } catch (Exception e) {
            e.printStackTrace();
            // 解析jwt令牌出错, 说明令牌过期或者伪造等不合法情况出现
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
        //12. 放行
        return chain.filter(exchange);
    }
}
