package mesosphere.marathon
package api

import java.security.cert.X509Certificate

import javax.inject.Inject
import javax.servlet._
import javax.servlet.http.HttpServletResponse
import sun.security.x509.X500Name

class SSLAuthFilter @Inject()(config: HttpConf) extends Filter {

  override def init(filterConfig: FilterConfig): Unit = {}

  override def doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain): Unit = {

    if (config.sslClientAuth()) {
      val cn = request.getAttribute("javax.servlet.request.X509Certificate").
        asInstanceOf[Array[X509Certificate]](0).getSubjectDN.asInstanceOf[X500Name].getCommonName

      if (!isCommonNameAllowed(cn)) {
        val responseToSend = response.asInstanceOf[HttpServletResponse]
        responseToSend.sendError(HttpServletResponse.SC_FORBIDDEN, "Client certificate is not allowed")
        return

      }
    }
    chain.doFilter(request, response)




  }

  private def isCommonNameAllowed(cn: String): Boolean = {
    config.allowedCNs().split(",").exists { allowedCn =>
      cn.matches(allowedCn.replaceAllLiterally("*", ".*"))
    }
  }


  override def destroy(): Unit = {}
}
