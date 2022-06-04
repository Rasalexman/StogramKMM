//
//  CreateView.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import SwiftUI
import Sodi

struct CreateView: BaseView {
    
    @ObservedObject private var vm: CreatePostViewModel = instance()
    
    var body: some View {
        HStack {
            Button(action: {
                vm.start()
            }, label: {
                Text("Add Random Pics")
            })
        }.frame(width: .infinity, height: .infinity, alignment: Alignment.center)
    }
}

struct CreateView_Previews: PreviewProvider {
    static var previews: some View {
        CreateView()
    }
}
