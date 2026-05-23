import SwiftUI
import Shared
import FirebaseCore
import FirebaseMessaging

@UIApplicationMain
class AppDelegate: NSObject, UIApplicationDelegate {
    var window: UIWindow?

    func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {

//         FirebaseApp.configure();

        KoinInitKt.doInitKoin();

        let appStarter = AppStarter.init();

        let prepared = appStarter.prepare();

        self.window = UIWindow(frame: UIScreen.main.bounds)
        let mainViewController = UIHostingController(rootView: ContentView(prepared: prepared))
        self.window!.rootViewController = mainViewController
        self.window!.makeKeyAndVisible()

        return true
    }

    func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data){
        Messaging.messaging().apnsToken = deviceToken;

        PushNotificationManagerCompanion().shared.setApnsToken();
    }

    func application(_ application: UIApplication, didReceiveRemoteNotification userInfo: [AnyHashable : Any], fetchCompletionHandler completionHandler: @escaping (UIBackgroundFetchResult) -> Void) {

        guard let aps = userInfo["aps"] as? [String: Any],
              let contentAvailable = aps["content-available"] as? Int, contentAvailable == 1 else {
            completionHandler(.noData);
            return
        };

        let newData = PushNotificationManagerCompanion().shared.onApplicationDidReceiveRemoteNotification(userInfo:userInfo);

        if newData{
            completionHandler(.newData);
        }else{
            completionHandler(.noData);
        }
    }
}
