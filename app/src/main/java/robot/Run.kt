package robot

import java.util.*

fun main(args: Array<String>) {
    val createRobot = CreateRobot()
    val robots: MutableList<Robot> = LinkedList()
    val expensive = Expensive()
    for (i in 0..2) {
        robots.add(createRobot.createRobot())
        println("Робот по имени " + robots[i].name + " создан!")
        robots[i].action()
    }
    println("Самый дорогой робот по имени " + expensive.expensiveRobot(robots)!!.name + " его стоимость " + expensive.expensiveRobot(robots)!!.priceRobot)
}