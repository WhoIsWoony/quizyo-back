package com.whoiswoony.springtutorial.controller.util

import javax.servlet.http.HttpServletRequest

val IP_HEADER_CANDIDATES = arrayOf(
    "X-Forwarded-For",
    "Proxy-Client-IP",
    "WL-Proxy-Client-IP",
    "HTTP_X_FORWARDED_FOR",
    "HTTP_X_FORWARDED",
    "HTTP_X_CLUSTER_CLIENT_IP",
    "HTTP_CLIENT_IP",
    "HTTP_FORWARDED_FOR",
    "HTTP_FORWARDED",
    "HTTP_VIA",
    "REMOTE_ADDR"
)
fun getIp(request: HttpServletRequest): String {
    for(header in IP_HEADER_CANDIDATES){
        val ip = request.getHeader(header)
        if (ip != null && ip.isNotEmpty() && !"unknown".equals(ip,true))
            return ip
    }
    return request.remoteAddr
}
