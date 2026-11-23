package zenovra.swordfight.net

object DelayedTasks {
    private class Task(var ticksLeft: Int, val action: () -> Unit)
    private val tasks = mutableListOf<Task>()

    fun schedule(delayTicks: Int, action: () -> Unit) {
        tasks.add(Task(delayTicks, action))
    }

    // call this once per server tick
    fun tick() {
        val iter = tasks.iterator()
        while (iter.hasNext()) {
            val t = iter.next()
            if (--t.ticksLeft <= 0) {
                iter.remove()
                t.action()
            }
        }
    }
}