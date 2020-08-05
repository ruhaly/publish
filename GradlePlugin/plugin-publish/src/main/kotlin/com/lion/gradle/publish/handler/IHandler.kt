/*
 * Copyright © 2020 www.lionaitech.com. All Rights Reserved.
 *
 * Created by 234109 on 2020/4/26.
 */

package com.lion.gradle.publish.handler

import org.gradle.api.Project

interface IHandler {
    fun apply(target: Project)
}