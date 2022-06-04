//
//  GeometryImageView.swift
//  iosApp
//
//  Created by Alexander on 04.06.2022.
//  Copyright © 2022 orgName. All rights reserved.
//

import SwiftUI
import URLImage
import shared

struct GeometryImageView: View {
    
    let imageUrl: URL
    var cWidth: CGFloat? = nil
    var cHeight: CGFloat? = nil
    
    var body: some View {
        GeometryReader { geometry in
            ZStack {
                URLImage(imageUrl) { image in
                    image.resizable()
                        .scaledToFill()
                        .frame(width: chooseWidth(geometry), height: chooseHeight(geometry), alignment:.center)
                        .clipped()
                }
            }.frame(width: chooseWidth(geometry), height: chooseHeight(geometry))
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

struct GeometryImageView_Previews: PreviewProvider {
    static var previews: some View {
        GeometryImageView(imageUrl: PostEntity.companion.createRandom(defaultUser: nil).takeFirstPhoto().toUrl()).previewLayout(PreviewLayout.sizeThatFits)
    }
}
