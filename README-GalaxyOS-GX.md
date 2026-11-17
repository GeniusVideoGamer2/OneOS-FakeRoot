# GalaxyOS GX Android TV Launcher
Android TV Box için launcher kaynak projesi (Android 8+). Ana ekran, yüklü uygulamalar ve GX Ayarlar ekranı içerir. Bu ilk sürüm referans tasarımın piksel düzeyinde kopyası değildir. Sistem ayarları Android'in kendi ayar sayfalarına yönlendirilir. Root gerekmez.

## Derleme
GitHub Actions > Build GalaxyOS GX APK > Run workflow. Tamamlanınca GalaxyOS-GX-APK artifact'ını indirin, app-debug.apk dosyasını çıkarın.

## ADB yükleme
adb install -r app-debug.apk

Ana ekran seçimi destekleniyorsa HOME düğmesiyle GalaxyOS GX'i seçin. Bu proje sistem ROM'unu değiştirmez.
