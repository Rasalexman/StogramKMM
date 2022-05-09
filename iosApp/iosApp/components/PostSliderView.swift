//
//  PostSliderView.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import SwiftUI

struct PostSliderView: View {
    
    var images: [PhotoModel]
    
    private let screenWidth = UIScreen.screenWidth
    
    var body: some View {
            TabView {
                ForEach(images, id: \.id) { item in
                    Image(item.photo)
                        .resizable()
                        .scaledToFill()
                        .frame(width: screenWidth, height: screenWidth, alignment:.center)
                        .clipped()
                    
                }
            }
            .frame(height: screenWidth, alignment: .topLeading)
           .tabViewStyle(PageTabViewStyle())
    }
}

struct PostSliderView_Previews: PreviewProvider {
    static var previews: some View {
        PostSliderView(images: [PhotoModel(), PhotoModel(), PhotoModel()])
    }
}
