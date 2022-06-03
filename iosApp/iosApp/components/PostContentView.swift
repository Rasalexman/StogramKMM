//
//  PostSliderView.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import SwiftUI
import URLImage
import shared

struct PostContentView: View {
    
    var post: PostEntity
    private var images: [PhotoModel] {
        return post.content.map { photo in
            PhotoModel(id: UUID().uuidString, url: photo.toUrl())
        }
    }
    
    private let screenWidth = UIScreen.screenWidth
    
    var body: some View {
            TabView {
                ForEach(images, id: \.id) { item in
                    GeometryImageView(imageUrl: item.url, cWidth: screenWidth, cHeight: screenWidth)
                }
            }
            .frame(width: screenWidth, height: screenWidth, alignment: .topLeading)
           .tabViewStyle(PageTabViewStyle())
    }
}

struct PostContentView_Previews: PreviewProvider {
    static var previews: some View {
        PostContentView(post: PostEntity.companion.createRandom()).previewLayout(PreviewLayout.sizeThatFits)
    }
}
