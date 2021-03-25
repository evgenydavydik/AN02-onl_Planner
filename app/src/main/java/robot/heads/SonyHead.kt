package robot.heads

class SonyHead(override var priceHead: Int) :IHead {
    val price: Int
        get() {
            return priceHead
        }

    override fun speek(): String {
        return "Говорит голова Sony"
    }
}