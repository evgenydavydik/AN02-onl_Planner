package robot.hands

class SamsungHand(override var priceHand: Int) : IHand {
    val price: Int
        get() {
            return priceHand
        }

    override fun upHand(): String {
        return "Разводит руки в стороны"
    }
}