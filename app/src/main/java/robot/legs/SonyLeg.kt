package robot.legs

class SonyLeg(override var priceLeg: Int) : ILeg {
    val price: Int
        get() {
            return priceLeg
        }

    override fun step(): String {
        return "Выполняет покачивающий шаг"
    }
}