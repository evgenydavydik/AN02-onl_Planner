package robot.heads

class ToshibaHead(override var priceHead: Int) :IHead {
    val price: Int
        get() {
            return priceHead
        }

    override fun speek(): String {
        return "Добро пожаловать!"
    }
}