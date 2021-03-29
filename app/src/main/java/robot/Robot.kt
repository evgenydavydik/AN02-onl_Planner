package robot

import robot.hands.IHand
import robot.heads.IHead
import robot.legs.ILeg


class Robot(iHead: IHead, iHand: IHand, iLeg: ILeg, name: String) : IRobot {
    override val priceRobot: Int
        get() {
            return head!!.priceHead + hand!!.priceHand + leg!!.priceLeg
        }
    val head: IHead? = iHead
        get() {
            return field
        }
    val hand: IHand? = iHand
        get() {
            return field
        }
    val leg: ILeg? = iLeg
        get() {
            return field
        }
    val name: String? = name
        get() {
            return field
        }

    override fun action() {
        println(head!!.speek())
        println(hand!!.upHand())
        println(leg!!.step())
    }
}