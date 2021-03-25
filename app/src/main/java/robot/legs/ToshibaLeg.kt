package robot.legs

class ToshibaLeg(override var priceLeg: Int) : ILeg {
    val price: Int
        get() {
            return priceLeg
        }

    override fun step(): String {
        return "Топ"
    }
}