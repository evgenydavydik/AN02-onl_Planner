package robot.heads

class SamsungHead(override var priceHead: Int) : IHead {
    val price: Int
        get() {
            return priceHead
        }

    override fun speek(): String {
        return "Добрый день ваше высочество!"
    }
}