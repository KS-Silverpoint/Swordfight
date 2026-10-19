execute as @a run execute store result score @s px run data get entity @s pos[0]
execute as @a run execute store result score @s py run data get entity @s pos[1]
execute as @a run execute store result score @s pz run data get entity @s pos[2]
execute as @a run execute store result storage swordfight:store x int 1 run random roll 1..2
execute as @a run execute if data storage swordfight:store {x:1} run execute in swordfight:alberhut run tp @s 28 1 148
execute as @a run execute if data storage swordfight:store {x:2} run execute in swordfight:alberhut run tp @s 34 1 -95