//
//  CircleImageView.swift
//  Stogram
//
//  Created by Alexander on 01.05.2022.
//

import SwiftUI

struct CircleImageView: View {
    
    var photo: String = Consts.USER_PHOTO
    var cWidth: CGFloat = Consts.DEFAULT_IMAGE_SIZE
    var cHeight: CGFloat = Consts.DEFAULT_IMAGE_SIZE
    var border: CGFloat = Consts.ZERO_SIZE
    var shadow: CGFloat = Consts.ZERO_SIZE
    var bColor: Color = Color.red
    
    var body: some View {
        Image(photo)
            .resizable()
            .aspectRatio(contentMode: .fill)
            .frame(width: cWidth, height: cHeight)
            .clipShape(Circle())
            .shadow(radius: shadow)
            .overlay(Circle().stroke(bColor, lineWidth: border))
    }
}

struct CircleImageView_Previews: PreviewProvider {
    static var previews: some View {
        CircleImageView()
    }
}
