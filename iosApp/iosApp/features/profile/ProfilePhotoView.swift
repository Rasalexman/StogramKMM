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
    let post : PostEntity
    
    var cWidth: CGFloat? = nil
    var cHeight: CGFloat? = nil
    
    private var imageUrl: URL {
        return URL(string: post.firstPhoto)!
    }
    
    var body : some View
    {
        ZStack(alignment: .bottomTrailing) {
            if #available(iOS 15.0, *) {
                Rectangle()
                    .aspectRatio(contentMode: .fit)
                    .foregroundColor(.clear)
                    .overlay {
                        GeometryReader { geometry in
                            URLImage(imageUrl) { image in
                                image.resizable()
                                    .scaledToFill()
                                    .frame(width: chooseWidth(geometry), height: chooseHeight(geometry), alignment:.center)
                                    .clipped()
                            }
                        }
                    }
            } else {
                GeometryReader { geometry in
                    URLImage(imageUrl) { image in
                        image.resizable()
                        .scaledToFill()
                        .frame(width: chooseWidth(geometry), height: chooseHeight(geometry), alignment:.center)
                        .clipped()
                    }
                }
            }
            
            if post.hasMoreContent {
                VStack(alignment: .trailing) {
                    Image(systemName: "doc.on.doc")
                        .resizable()
                        .frame(width: 14, height: 16, alignment: .bottomLeading)
                        .foregroundColor(.white)
                        .padding([.bottom, .trailing], 4)
                }
            }
        }
    }
    
    private func chooseWidth(_ proxy: GeometryProxy) -> CGFloat {
        if let currentWidth = cWidth {
            return currentWidth
        }
        return proxy.size.width
    }
    
    private func chooseHeight(_ proxy: GeometryProxy) -> CGFloat {
        if let currentHeight = cHeight  {
            return currentHeight
        }
        return proxy.size.height
    }
}

struct ProfilePhotoView_Previews: PreviewProvider {
    static var previews: some View {
        ProfilePhotoView(post: PostEntity.companion.createRandom())
    }
}
