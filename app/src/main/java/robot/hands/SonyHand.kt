package robot.hands

class SonyHand(override var priceHand: Int) :IHand {
    val price: Int
        get() {
            return priceHand
        }

    override fun upHand(): String {
        return "Вертит руками"
    }
}