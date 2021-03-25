package robot.hands

class ToshibaHand(override var priceHand: Int) :IHand {
    val price: Int
        get() {
            return priceHand
        }

    override fun upHand(): String {
        return "Машет руками!"
    }
}