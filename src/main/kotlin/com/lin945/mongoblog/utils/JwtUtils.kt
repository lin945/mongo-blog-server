package com.lin945.mongoblog.utils

import com.lin945.mongoblog.pojo.UserDO
import java.util.*

public fun createUserToken(userDO: UserDO):String {

    return ""
}

public fun RandomId(): String {
    val random = Random()
    var Random_generation_id = ""
    for (i in 1..32) {
        val letterORnumber = if (random.nextInt(2) % 2 == 0) "letter" else "number"
        if (letterORnumber.equals("letter", ignoreCase = true)) {
            val Random_num = if (random.nextInt(2) % 2 == 0) 65 else 97
            Random_generation_id += (Random_num + random.nextInt(26)).toChar()
        } else if (letterORnumber.equals("number", ignoreCase = true)) {
            Random_generation_id += random.nextInt(10).toString()
        }
    }
    //	System.out.println(Random_generation_id);
    return Random_generation_id
}