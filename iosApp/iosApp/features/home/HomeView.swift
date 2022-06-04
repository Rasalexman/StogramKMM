//
//  HomeView.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import SwiftUI
import Sodi

struct HomeView: BaseView {
    
    @ObservedObject private var vm: HomeViewModel = instance()
    
    init() {
        UITableView.appearance().showsVerticalScrollIndicator = false
    }
    
    private let dividerWidth = UIScreen.screenWidth-8
    
    var body: some View {
        
        ScrollView(.vertical, showsIndicators: false) {
            VStack(alignment: .leading, spacing: 0) {
                Divider()
                //------
                ScrollView(.horizontal, showsIndicators: false) {
                    LazyHStack {
                        ForEach(vm.userStories, id: \.id) { user in
                            UserAvatarView(photoUrl: user.photo.toUrl(), cWidth: Consts.STORY_IMAGE_SIZE, cHeight: Consts.STORY_IMAGE_SIZE, border: Consts.STORY_BORDER_SIZE)
                        }
                    }.padding(8)
                }
                Divider()
                
                //------
                LazyVStack(alignment: .trailing, spacing: 0) {
                    ForEach(vm.userPosts, id: \.id) { post in
                        PostView(post: post)
                            .listRowInsets(EdgeInsets(top: 0, leading: 0, bottom: 0, trailing: 0))
                        
                        Divider()
                            .frame(width: dividerWidth, height: 2, alignment: .trailing)
                    }
                }
            }
        }.onAppear {
            vm.start()
        }.onDisappear {
            vm.stop()
        }
    }
}

struct HomeView_Previews: PreviewProvider {
    static var previews: some View {
        HomeView()
    }
}
