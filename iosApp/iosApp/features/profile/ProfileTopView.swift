//
//  ProfileTopView.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import SwiftUI
import Sodi

struct ProfileTopView: BaseView {
    
    @ObservedObject private var vm: ProfileViewModel = instance()
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            HStack {
                CircleImageView(cWidth: 78, cHeight: 78)
                
                TextCounterView(count: $vm.postCount, desc: "Публикации")
                TextCounterView(count: $vm.subsCount, desc: "Подписки")
                TextCounterView(count: $vm.observCount, desc: "Наблюдатели")
            }.frame(maxWidth: .infinity, alignment: .topLeading)
            
            Text(vm.userName)
                .font(.system(size: 16))
                .bold()
                .padding(EdgeInsets(top:8, leading: 0, bottom: 4, trailing: 0))
            
            Text(vm.userDesc)
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
