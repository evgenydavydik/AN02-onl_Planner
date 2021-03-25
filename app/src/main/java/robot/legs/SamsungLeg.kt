package robot.legs

class SamsungLeg(override var priceLeg: Int) : ILeg {
    val price: Int
        get() {
            return priceLeg
        }

    override fun step(): String {
        return "Делает шаг прихрамывая"
    }
}