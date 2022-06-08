//
//  LikesView.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import SwiftUI
import Sodi

struct ReactionsView: View {
    @ObservedObject private var vm: ReactionsViewModel = instance()
    
    var body: some View {
        VStack(alignment: .leading, spacing: 0) {
            //Divider()
            //------
            List {
                ForEach(vm.reactions, id: \.id) { reaction in
                    ReactionItemView(reaction: reaction).listRowInsets(EdgeInsets())
                }
            }.listStyle(.plain)
        }.onAppear {
            vm.start()
        }.onDisappear {
            vm.stop()
        }
    }
}

struct ReactionsView_Previews: PreviewProvider {
    static var previews: some View {
        ReactionsView()
    }
}
