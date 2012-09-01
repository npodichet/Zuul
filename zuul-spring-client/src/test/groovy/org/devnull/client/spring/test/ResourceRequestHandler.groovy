package org.devnull.client.spring.test

import org.mortbay.jetty.handler.AbstractHandler
import org.springframework.core.io.Resource

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class ResourceRequestHandler extends AbstractHandler {
    Resource resource

    void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch) {
        response.status = HttpServletResponse.SC_OK
        response.outputStream << resource.inputStream

    }
}
