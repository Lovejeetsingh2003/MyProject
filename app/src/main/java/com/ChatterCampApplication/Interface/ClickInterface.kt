package com.ChatterCampApplication.Interface

import com.ChatterCampApplication.DataClass.RegisterParticipantDataClass
import com.ChatterCampApplication.DataClass.RegisterTrainerDataClass
import com.ChatterCampApplication.DataClass.WorkshopList

interface ClickInterfaceHome {
    fun showData(workshopEntity: WorkshopList)
}
interface ClickInterfaceParticipant{
    fun showDataParticipant(registerParticipantEntity: RegisterParticipantDataClass)
}
interface ClickInterfaceTrainer{
    fun showDataTrainer(registerTrainerEntity: RegisterTrainerDataClass)
}

