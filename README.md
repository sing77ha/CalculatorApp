# เครื่องคิดเลข Android

แอปเครื่องคิดเลขพื้นฐานภาษาไทย เขียนด้วย Kotlin และ Android Views โดยไม่ต้องติดตั้งไลบรารี UI เพิ่มเติม

## วิธีสร้าง APK
1. ติดตั้ง Android Studio รุ่นล่าสุด
2. เปิดโฟลเดอร์ `CalculatorApp` ด้วย Android Studio
3. รอให้ Gradle Sync เสร็จ (ต้องเชื่อมต่ออินเทอร์เน็ตครั้งแรกเพื่อดาวน์โหลด Android Gradle Plugin และ SDK ที่จำเป็น)
4. เลือก **Build > Build Bundle(s) / APK(s) > Build APK(s)**
5. เมื่อ Build สำเร็จ เลือก **locate** เพื่อเปิดโฟลเดอร์ APK

ไฟล์ APK ปกติจะอยู่ที่ `app/build/outputs/apk/debug/app-debug.apk`

## ความสามารถ
- บวก ลบ คูณ หาร
- เปอร์เซ็นต์ และเปลี่ยนเครื่องหมายบวก/ลบ
- ลบตัวเลขทีละหลัก และล้างค่า
- รองรับ Android 6.0 ขึ้นไป (API 23)

หมายเหตุ: ไฟล์ ZIP นี้เป็นซอร์สโปรเจกต์ Android Studio ยังไม่ได้คอมไพล์เป็น APK ในสภาพแวดล้อมนี้
