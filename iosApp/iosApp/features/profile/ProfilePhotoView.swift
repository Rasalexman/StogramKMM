//
//  ProfilePhotoView.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import SwiftUI
import shared
import URLImage

struct ProfilePhotoView: BaseView {
    @State var photoUrl: URL
    @State var hasMoreContent: Bool
    
    var cWidth: CGFloat? = nil
    var cHeight: CGFloat? = nil
    
    var body : some View
    {
        ZStack(alignment: .bottomTrailing) {
            if #available(iOS 15.0, *) {
                Rectangle()
                    .aspectRatio(contentMode: .fit)
                    .foregroundColor(.clear)
                    .overlay {
                        GeometryImageView(imageUrl: photoUrl, cWidth: cWidth, cHeight: cHeight)
                    }
            } else {
                GeometryImageView(imageUrl: photoUrl, cWidth: cWidth, cHeight: cHeight)
            }
            
            if hasMoreContent {
                VStack(alignment: .trailing) {
                    Image(systemName: Consts.IMAGE_MORE_PICS)
                        .resizable()
                        .frame(width: 14, height: 16, alignment: .bottomLeading)
                        .foregroundColor(.white)
                        .padding([.bottom, .trailing], 4)
                }
            }
        }
    }
}

struct ProfilePhotoView_Previews: PreviewProvider {
    static let post = PostEntity.companion.createRandom(defaultUser: nil)
    
    static var previews: some View {
        ProfilePhotoView(photoUrl: post.takeFirstPhoto().toUrl(), hasMoreContent: post.hasMoreContent() ).previewLayout(PreviewLayout.sizeThatFits)
    }
}
