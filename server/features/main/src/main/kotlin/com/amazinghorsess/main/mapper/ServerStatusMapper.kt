package com.amazinghorsess.main.mapper

import android.view.View
import com.amazinghorsess.main.model.ServerStatus as ViewServerStatus
import com.amazinghorsess.domain.model.ServerStatus as DomainServerStatus

internal class ServerStatusMapper {

    fun toView(domainServerStatus: DomainServerStatus): ViewServerStatus =
        when(domainServerStatus){
            DomainServerStatus.RUNNING -> ViewServerStatus.RUNNING
            DomainServerStatus.STOPPED -> ViewServerStatus.STOPPED
        }




}