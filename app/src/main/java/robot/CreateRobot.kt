package robot

import robot.hands.SamsungHand
import robot.hands.SonyHand
import robot.hands.ToshibaHand
import robot.heads.SamsungHead
import robot.heads.SonyHead
import robot.heads.ToshibaHead
import robot.legs.SamsungLeg
import robot.legs.SonyLeg
import robot.legs.ToshibaLeg
import java.util.*


class CreateRobot {
    private val iHands = arrayOf(SamsungHand(500), SonyHand(300), ToshibaHand(200))
    private val iLegs = arrayOf(SamsungLeg(800), SonyLeg(650), ToshibaLeg(450))
    private val iHeads = arrayOf(SamsungHead(1000), SonyHead(750), ToshibaHead(500))

    fun createRobot(): Robot {
        val scanner = Scanner(System.`in`)
        println("Введите имя робота")
        val name: String = scanner.nextLine()
        val random = Random()
        return Robot(iHeads[random.nextInt(3)], iHands[random.nextInt(3)], iLegs[random.nextInt(3)], name)
    }
}