//
//  ProfileTopView.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import SwiftUI
import Sodi
import shared

struct ProfileTopView: BaseView {
    
    @ObservedObject private var vm: ProfileViewModel = instance()
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            HStack {
                UserAvatarView(user: vm.selectedUser, cWidth: Consts.PROFILE_IMAGE_SIZE, cHeight: Consts.PROFILE_IMAGE_SIZE)
                
                TextCounterView(count: $vm.selectedUser.postCount, desc: "Публикации")
                TextCounterView(count: $vm.selectedUser.subsCount, desc: "Подписки")
                TextCounterView(count: $vm.selectedUser.observCount, desc: "Наблюдатели")
            }.frame(maxWidth: .infinity, alignment: .topLeading)
            
            Text(vm.selectedUser.name)
                .font(.system(size: 16))
                .bold()
                .padding(EdgeInsets(top:8, leading: 0, bottom: 4, trailing: 0))
            
            Text(vm.selectedUser.bio)
                .font(.system(size: 12))
                .padding(EdgeInsets(top:0, leading: 0, bottom: 10, trailing: 0))
            
        }
    }
}

struct ProfileTopView_Previews: PreviewProvider {
    static var previews: some View {
        ProfileTopView()
    }
}
