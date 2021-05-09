package brisksoft.com.ad340

data class TrafficCam(val description: String, private val image: String, var type: String, var coords: DoubleArray ) {

    private val baseUrl: Map<String, String> = mapOf(
        "sdot" to "http://www.seattle.gov/trafficcams/images/",
        "wsdot" to "http://images.wsdot.wa.gov/nw/"
    )

    fun imageUrl() : String {
        return baseUrl[this.type] + this.image
    }
    companion object {
        var dataUrl = " https://web6.seattle.gov/Travelers/api/Map/Data?zoomId=13&type=2"
    }
}
