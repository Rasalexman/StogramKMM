//
//  ProfileView.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import SwiftUI
import Sodi
import shared

struct ProfileView: BaseView {
    
    var profileId: String = UserEntity.companion.DEFAULT_USER_ID
    @ObservedObject private var vm: ProfileViewModel = instance()
    
    private let threeColumnGrid = [
        GridItem(.flexible(minimum: 40), spacing: 0),
        GridItem(.flexible(minimum: 40), spacing: 0),
        GridItem(.flexible(minimum: 40), spacing: 0),
    ]
    
    var body: some View {
        
        ZStack {
            if let currentUser = vm.selectedUser {
                ScrollView(.vertical, showsIndicators: false) {
                    VStack(alignment: .leading, spacing: 0) {
                        Divider()
                        ///---- top layout
                        ProfileTopView(selectedUser: currentUser)
                            .padding(EdgeInsets(top:8, leading: 8, bottom: 0, trailing: 8))
                            .frame(minWidth: 0, maxWidth: .infinity, alignment: .topLeading)
                        
                        //--- photo view
                        LazyVGrid(columns: threeColumnGrid, alignment: .leading, spacing: 0) {
                            ForEach(vm.userPosts, id: \.id) { postModel in
                                NavigationLink(
                                    destination: PostDetailsView(selectedPost: postModel, showHeader: false, showCommentsCount: false)
                                ) {
                                    ProfilePhotoView(post: postModel)
                                }
                            }
                        }
                    }
                }.frame(minWidth: 0, maxWidth: .infinity, minHeight: 0, maxHeight: .infinity, alignment: .topLeading)
            } else {
                ProgressView().progressViewStyle(CircularProgressViewStyle())
                    .frame(width: Consts.PROFILE_IMAGE_SIZE, height: Consts.PROFILE_IMAGE_SIZE, alignment: Alignment.center)
            }
            
                
        }.onAppear {
            vm.fetchProfileData(userId: profileId)
        }.onDisappear {
            vm.stop()
        }
        
        
    }
}

struct ProfileView_Previews: PreviewProvider {
    static var previews: some View {
        ProfileView()
    }
}
