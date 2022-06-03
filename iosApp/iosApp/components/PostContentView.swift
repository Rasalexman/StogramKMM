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
            PhotoModel(id: UUID().uuidString, url: URL(string: photo)!)
        }
    }
    
    private let screenWidth = UIScreen.screenWidth
    
    var body: some View {
            TabView {
                ForEach(images, id: \.id) { item in
                    URLImage(item.url) { image in
                        image.resizable()
                            .scaledToFill()
                            .frame(width: screenWidth, height: screenWidth, alignment:.center)
                            .clipped()
                        
                    }
                }
            }
            .frame(height: screenWidth, alignment: .topLeading)
           .tabViewStyle(PageTabViewStyle())
    }
}

struct PostContentView_Previews: PreviewProvider {
    static var previews: some View {
        PostContentView(post: PostEntity.companion.createRandom())
    }
}
