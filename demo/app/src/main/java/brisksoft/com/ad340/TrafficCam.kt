package brisksoft.com.ad340

class TrafficCam(val description: String, val image: String, var type: String, var coords: DoubleArray ) {

    val baseUrl: Map<String, String> = mapOf(
        "sdot" to "http://www.seattle.gov/trafficcams/images/",
        "wsdot" to "http://images.wsdot.wa.gov/nw/"
    )

    public fun imageUrl() : String {
        return baseUrl[this.type] + this.image
    }
}
