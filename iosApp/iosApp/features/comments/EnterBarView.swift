//
//  EnterBarView.swift
//  Stogram
//
//  Created by Alexander on 02.05.2022.
//

import SwiftUI

struct EnterBarView: View {
    
    @Binding var enteredText: String
    
    @State private var isEditing = false
    
    var body: some View {
        
        HStack(alignment: .center, spacing: 0) {
            TextField("Введите текст...", text: $enteredText)
                .padding(7)
                .padding([.leading], 8)
                .background(Color(.systemGray6))
                .cornerRadius(8)
                .buttonStyle(.borderless)
                .overlay(
                    HStack {
                        if isEditing {
                            Button(action: {
                                self.enteredText = ""
                            }) {
                                Image(systemName: "multiply.circle.fill")
                                    .foregroundColor(.gray)
                                    .padding(.trailing, 8)
                            }
                        }
                    }.frame(minWidth: 0, maxWidth: .infinity, alignment: .trailing)
                )
                .padding(.horizontal, 10)
                .padding(.vertical, 8)
                .onTapGesture {
                    self.isEditing = true
                }
            
            Button(action: {
                // Dismiss the keyboard
                UIApplication.shared.sendAction(#selector(UIResponder.resignFirstResponder), to: nil, from: nil, for: nil)
                self.isEditing = false
                self.enteredText = ""
                
            }) {
                Image(systemName: "arrowtriangle.forward.fill")
                    .resizable()
                    .frame(width: 24, height: 24, alignment: .center)
                    .foregroundColor(.blue)
            }
            .padding(.trailing, 8)
            .transition(.move(edge: .trailing))
            .animation(Animation.default, value: 10)
        }
        
    }
}

struct EnterBarView_Previews: PreviewProvider {
    static var previews: some View {
        EnterBarView(enteredText: .constant("")).previewLayout(PreviewLayout.sizeThatFits)
    }
}
