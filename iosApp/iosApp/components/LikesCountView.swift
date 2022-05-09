//
//  LikesCountView.swift
//  Stogram
//
//  Created by Alexander on 02.05.2022.
//

import SwiftUI

struct LikesCountView: View {
    
    var isLiked: Binding<Bool>? = nil
    var count: String = "100"
    var paddingStart: CGFloat = 4
    var actionHandler: (() -> Void)? = nil
    
    var body: some View {
        Button(action: actionHandler ?? {}, label: {
            Image(systemName: isLiked?.wrappedValue == true ? Consts.IMAGE_LIKE_FILLED : Consts.IMAGE_LIKE)
                .resizable()
                .frame(width: 24, height: 22)
            
            Text(count)
                .font(.system(size: 12))
                .padding(EdgeInsets(top:4, leading: 0, bottom: 4, trailing: 0))
            
        }).padding(EdgeInsets(top:4, leading: paddingStart, bottom: 4, trailing: 8))
            .buttonStyle(.borderless)
    }
}

struct LikesCountView_Previews: PreviewProvider {
    static var previews: some View {
        LikesCountView().previewLayout(PreviewLayout.sizeThatFits)
    }
}
