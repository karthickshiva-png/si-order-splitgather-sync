import com.sap.gateway.ip.core.customdev.util.Message
import groovy.util.XmlSlurper

def Message processData(Message message) {

    def body = message.getBody(String)
    def xml = new XmlSlurper().parseText(body)

    def filters = []

    xml.orders.each { order ->
        def orderNo = order.orderNo.text()
        filters.add("OrderID eq ${orderNo}")
    }

    if (filters) {
        def filterQuery = "\$filter=" + filters.join(" or ")
        message.setProperty("filterQuery", filterQuery)
    }

    return message
}