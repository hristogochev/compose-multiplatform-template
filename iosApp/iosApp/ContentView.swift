import UIKit
import SwiftUI
import Shared

struct ComposeView: UIViewControllerRepresentable {
    let prepared: AppPrepared

    init(prepared: AppPrepared) {
        self.prepared = prepared
    }

    func makeUIViewController(context: Context) -> UIViewController {
        return MainViewControllerKt.MainViewController(prepared: prepared);
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    let prepared: AppPrepared

    var body: some View {
        ComposeView(prepared: prepared)
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
                .ignoresSafeArea()
    }
}


