package com.example.sweater.service;

import com.example.sweater.controller.GreetingController;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static java.lang.System.out;

@Component
@Order(1)
@WebFilter(filterName = "IpFilter")
public class IpFilter implements Filter {

    private ReadIpAddressFromFile readIpAddressFromFile;
    private GreetingController greetingController;
    static List<String> ipAddress;

    public IpFilter(ReadIpAddressFromFile readIpAddressFromFile, GreetingController greetingController) {
        this.readIpAddressFromFile = readIpAddressFromFile;
        this.greetingController = greetingController;
    }

    public void destroy() {
    }
    @Scheduled(fixedRate = 5000)
    public void getIp (){
       ipAddress = readIpAddressFromFile.getIpAddress();
    }


    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        resp.setContentType(greetingController.greeting());

        String userIp = req.getRemoteAddr();
        HttpServletResponse httpResponse = null;
        if (resp instanceof HttpServletResponse) {
            httpResponse = (HttpServletResponse) resp;
        }

        if (ipAddress.contains(userIp)) {
            assert httpResponse != null;
            httpResponse.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not allowed to access the servlet!");
        } else {
            chain.doFilter(req, resp);
            greetingController.greeting();

        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
