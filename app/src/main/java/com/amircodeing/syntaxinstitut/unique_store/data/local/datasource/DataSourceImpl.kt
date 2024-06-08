package com.amircodeing.syntaxinstitut.unique_store.data.local.datasource

import com.amircodeing.syntaxinstitut.unique_store.R
import com.amircodeing.syntaxinstitut.unique_store.data.model.Category
import com.amircodeing.syntaxinstitut.unique_store.data.model.Setting

class DataSourceImpl : DataSource {
    override fun getCategories(): List<Category> {
        return listOfCategory
    }

    override fun getSettingElements(): List<Setting> {
     return lisOfSettings
    }

    private val listOfCategory = listOf(
        Category(
            1,
            "MEN'S",
            "https://b2292265.smushcdn.com/2292265/wp-content/uploads/2022/06/seo-ppc-fashion-retailers-blog-header.jpg?lossy=1&strip=1&webp=1"
        ),
        Category(2, "WOMEN'S", "https://live.staticflickr.com/5299/5480740607_0d3e7a8c40_z.jpg"),
        Category(
            3,
            "JEWELERY",
            "https://i.pinimg.com/736x/96/24/c6/9624c6b0b7ba7ea35932081fc25f7ac2.jpg"
        ),
        Category(
            4,
            "ELECTRONIC",
            "https://images.unsplash.com/photo-1468495244123-6c6c332eeece?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8M3x8ZWxlY3Ryb25pYyUyMGRldmljZXN8ZW58MHx8MHx8fDA%3D"
        )
    )

    private val lisOfSettings = listOf(
        Setting(
            1,
            "Terms and Conditions",
            "\n" +
                    "Certainly! Below is a template for the \"Terms and Conditions\" for an online shopping website. You can customize it according to your specific requirements and legal needs. It's always a good idea to have a legal professional review your terms and conditions to ensure compliance with local laws and regulations.\n" +
                    "\n" +
                    "Terms and Conditions\n" +
                    "\n" +
                    "1. Introduction\n" +
                    "Welcome to [Your Online Shop Name]. These Terms and Conditions govern your use of our website and the purchase of products from our online store. By accessing or using our website, you agree to comply with and be bound by these Terms and Conditions.\n" +
                    "\n" +
                    "2. Use of the Website\n" +
                    "You agree to use the website for lawful purposes only. You must not use the website in any way that breaches any applicable local, national, or international law or regulation.\n" +
                    "\n" +
                    "3. Account Registration\n" +
                    "To make a purchase, you may need to create an account. You agree to provide accurate and complete information during the registration process and to keep your account information updated. You are responsible for maintaining the confidentiality of your account password and for all activities that occur under your account.\n" +
                    "\n" +
                    "4. Orders and Payment\n" +
                    "All orders are subject to acceptance and availability. We reserve the right to refuse or cancel any order for any reason. Prices for products are subject to change without notice. Payments can be made using the payment methods specified on our website.\n" +
                    "\n" +
                    "5. Shipping and Delivery\n" +
                    "We aim to process and ship your order within [number of days] business days. Delivery times may vary depending on your location and shipping method selected. We are not responsible for delays caused by the shipping carrier or customs.\n" +
                    "\n" +
                    "6. Returns and Refunds\n" +
                    "We accept returns within [number of days] days of delivery, provided the product is in its original condition and packaging. To initiate a return, please contact our customer service team. Refunds will be processed within [number of days] days of receiving the returned item.\n" +
                    "\n" +
                    "7. Product Descriptions\n" +
                    "We strive to provide accurate descriptions of our products. However, we do not warrant that the product descriptions or other content on the website are accurate, complete, reliable, current, or error-free.\n" +
                    "\n" +
                    "8. Intellectual Property\n" +
                    "All content on this website, including text, graphics, logos, images, and software, is the property of [Your Online Shop Name] or its content suppliers and is protected by intellectual property laws. You may not reproduce, distribute, or create derivative works from any content on this website without our prior written consent.\n" +
                    "\n" +
                    "9. Limitation of Liability\n" +
                    "To the fullest extent permitted by law, [Your Online Shop Name] shall not be liable for any indirect, incidental, special, or consequential damages arising out of or in connection with your use of our website or the purchase of products from our online store.\n" +
                    "\n" +
                    "10. Governing Law\n" +
                    "These Terms and Conditions are governed by and construed in accordance with the laws of [Your Country/State], and you agree to submit to the exclusive jurisdiction of the courts located in [Your Country/State].\n" +
                    "\n" +
                    "11. Changes to Terms and Conditions\n" +
                    "We reserve the right to modify these Terms and Conditions at any time. Any changes will be posted on this page, and your continued use of the website after such changes have been posted constitutes your acceptance of the new Terms and Conditions.\n" +
                    "\n" +
                    "12. Contact Information\n" +
                    "If you have any questions about these Terms and Conditions, please contact us at [Your Contact Information].\n" +
                    "\n", R.drawable.iconamoon_arrow_down_thin , R.drawable.terms_and_conditions
        ),

                Setting(
                2,
        "usage policies",
                    "Data Protection Policy\n" +
                            "\n" +
                            "1. Introduction\n" +
                            "At [Your Online Shop Name], we are committed to protecting your personal data and respecting your privacy. This Data Protection Policy explains how we collect, use, disclose, and safeguard your information when you visit our website or make a purchase from our online store.\n" +
                            "\n" +
                            "2. Data We Collect\n" +
                            "We may collect and process the following data about you:\n" +
                            "\n" +
                            "Personal Identification Information: Name, email address, phone number, billing and shipping address.\n" +
                            "Payment Information: Credit card details, billing address, and other financial information required to process your order.\n" +
                            "Technical Data: IP address, browser type, and version, time zone setting, browser plug-in types and versions, operating system and platform, and other technology on the devices you use to access this website.\n" +
                            "Usage Data: Information about how you use our website, products, and services.\n" +
                            "Marketing and Communications Data: Your preferences in receiving marketing from us and your communication preferences.\n" +
                            "3. How We Use Your Data\n" +
                            "We use the information we collect in the following ways:\n" +
                            "\n" +
                            "To Process Transactions: To manage payments, fees, and charges and to collect and recover money owed to us.\n" +
                            "To Manage Your Account: To create and manage your account, including verifying your identity and providing customer support.\n" +
                            "To Improve Our Services: To analyze and improve our website, products, services, marketing, customer relationships, and experiences.\n" +
                            "To Communicate with You: To send you updates, promotional materials, and other information that may be of interest to you.\n" +
                            "To Comply with Legal Obligations: To comply with applicable laws and regulations.\n" +
                            "4. How We Share Your Data\n" +
                            "We may share your data with third parties under the following circumstances:\n" +
                            "\n" +
                            "Service Providers: We may share your information with third-party service providers who perform services on our behalf, such as payment processing, data analysis, email delivery, hosting services, customer service, and marketing assistance.\n" +
                            "Business Transfers: In the event of a merger, acquisition, or sale of all or a portion of our assets, your personal data may be transferred to the new owner.\n" +
                            "Legal Requirements: We may disclose your information if required to do so by law or in response to valid requests by public authorities.\n" +
                            "5. Data Security\n" +
                            "We have implemented appropriate security measures to prevent your personal data from being accidentally lost, used, or accessed in an unauthorized way, altered, or disclosed. We also limit access to your personal data to those employees, agents, contractors, and other third parties who have a business need to know.\n" +
                            "\n" +
                            "6. Data Retention\n" +
                            "We will only retain your personal data for as long as necessary to fulfill the purposes we collected it for, including for the purposes of satisfying any legal, accounting, or reporting requirements.\n" +
                            "\n" +
                            "7. Your Data Protection Rights (continued)\n" +
                            "Object to Processing: The right to object to our processing of your personal data, under certain conditions.\n" +
                            "Data Portability: The right to request that we transfer the data that we have collected to another organization, or directly to you, under certain conditions.\n" +
                            "If you make a request, we have one month to respond to you. If you would like to exercise any of these rights, please contact us at our provided contact information below.\n" +
                            "\n" +
                            "8. Cookies and Tracking Technologies\n" +
                            "Our website uses cookies and similar tracking technologies to enhance your browsing experience and collect information about how you use our site. Cookies are small data files stored on your device. You can set your browser to refuse cookies, but some features of our site may not function properly as a result. For more information about the cookies we use, please refer to our Cookie Policy.\n" +
                            "\n" +
                            "9. International Data Transfers\n" +
                            "Your information, including personal data, may be transferred to, and maintained on, computers located outside of your state, province, country, or other governmental jurisdiction where the data protection laws may differ from those in your jurisdiction. By submitting your information to us, you consent to these transfers.\n" +
                            "\n" +
                            "10. Changes to This Data Protection Policy\n" +
                            "We may update our Data Protection Policy from time to time. We will notify you of any changes by posting the new Data Protection Policy on this page. We will let you know via email and/or a prominent notice on our website, prior to the change becoming effective and update the \"effective date\" at the top of this Data Protection Policy. You are advised to review this Data Protection Policy periodically for any changes.\n" +
                            "\n" +
                            "11. Contact Information\n" +
                            "If you have any questions about this Data Protection Policy, the data we hold on you, or you would like to exercise one of your data protection rights, please do not hesitate to contact us."
        , R.drawable.iconamoon_arrow_down_thin , R.drawable.mobile_usage
    ),

        Setting(3, "Imprint" ,"Imprint\n" +
                "\n" +
                "1. Information According to § 5 TMG (German Telemedia Act)\n" +
                "\n" +
                "U-Store\n" +
                "Amir Lotfi\n" +
                "Eduard-bernstein-Str 78\n" +
                "28329, Bremen\n" +
                "Germany\n" +
                "\n" +
                "2. Contact Information\n" +
                "\n" +
                "Phone: 0049421 22222-21\n" +
                "Fax: 0049421 33333-3\n" +
                "Email: amirrllotfi@gmail.com\n" +
                "Website: www.u-store.com\n" +
                "\n" +
                "3. Register Entry\n" +
                "\n" +
                "Register Court: [Name of the Register Court]\n" +
                "Register Number: [Your Register Number]\n" +
                "\n" +
                "4. VAT Identification Number\n" +
                "\n" +
                "VAT ID: [Your VAT Identification Number according to §27 a Value Added Tax Act]\n" +
                "\n" +
                "5. Responsible for Content According to § 55 Abs. 2 RStV (German Interstate Broadcasting Agreement)\n" +
                "\n" +
                "[Name of the Person Responsible]\n" +
                "[Street Address]\n" +
                "[Postal Code, City]\n" +
                "[Country]\n" +
                "\n" +
                "6. Dispute Resolution\n" +
                "\n" +
                "The European Commission provides a platform for online dispute resolution (ODR): https://ec.europa.eu/consumers/odr.\n" +
                "Our email address can be found above.\n" +
                "\n" +
                "We are neither willing nor obliged to participate in dispute resolution proceedings before a consumer arbitration board.\n" +
                "\n" +
                "7. Disclaimer\n" +
                "\n" +
                "Content Liability\n" +
                "\n" +
                "As a service provider, we are responsible for our own content on these pages according to § 7 Abs.1 TMG. However, according to §§ 8 to 10 TMG, we are not obliged to monitor transmitted or stored third-party information or to investigate circumstances that indicate illegal activity.\n" +
                "\n" +
                "Obligations to remove or block the use of information under general laws remain unaffected. However, liability in this regard is only possible from the time of knowledge of a specific infringement. Upon notification of corresponding legal violations, we will remove this content immediately.\n" +
                "\n" +
                "Link Liability\n" +
                "\n" +
                "Our offer contains links to external websites of third parties over whose contents we have no influence. Therefore, we cannot assume any liability for these external contents. The respective provider or operator of the pages is always responsible for the contents of the linked pages. The linked pages were checked for possible legal violations at the time of linking. Illegal contents were not recognizable at the time of linking.\n" +
                "\n" +
                "A permanent control of the content of the linked pages is not reasonable without concrete evidence of a violation. Upon notification of legal violations, we will remove such links immediately.\n" +
                "\n" +
                "Copyright\n" +
                "\n" +
                "The content and works on these pages created by the site operators are subject to German copyright law. The duplication, processing, distribution, and any kind of exploitation outside the limits of copyright require the written consent of the respective author or creator. Downloads and copies of this site are only permitted for private, non-commercial use.\n" +
                "\n" +
                "As far as the content on this site was not created by the operator, the copyrights of third parties are respected. In particular, contents of third parties are marked as such. Should you nevertheless become aware of a copyright infringement, we request that you notify us accordingly. Upon notification of legal violations, we will remove such content immediately.\n" +
                "\n",R.drawable.iconamoon_arrow_down_thin , R.drawable.material_symbols_light_info_outline),
        Setting(4, "Help" ,"About Our Online Shop\n" +
                "\n" +
                "Welcome to [Your Online Shop Name]\n" +
                "\n" +
                "At [Your Online Shop Name], we are passionate about [describe your mission and what sets you apart from others in your industry]. Our goal is to provide [describe the type of products you offer, e.g., high-quality fashion items, sustainable home goods, cutting-edge tech gadgets, etc.] that delight our customers with their quality, uniqueness, and affordability.\n" +
                "\n" +
                "Our Mission\n" +
                "\n" +
                "Our mission is to [state your mission, e.g., \"to make high-quality fashion accessible to everyone,\" \"to promote sustainable living through eco-friendly products,\" etc.]. We strive to [mention your core values, e.g., \"promote sustainability,\" \"ensure customer satisfaction,\" \"support local artisans,\" etc.] in everything we do.\n" +
                "\n" +
                "Why Choose Us?\n" +
                "\n" +
                "Quality: We carefully select each product to ensure the highest quality and durability.\n" +
                "Unique Selection: Our curated collection offers unique and hard-to-find items that you won't find elsewhere.\n" +
                "Affordability: We believe in offering great value for money with competitive prices.\n" +
                "Customer Service: Our dedicated customer service team is always ready to assist you with any questions or concerns.\n" +
                "Secure Shopping: Shop with confidence knowing that your personal information is secure.\n" +
                "Our Promise\n" +
                "\n" +
                "We are committed to providing you with an exceptional shopping experience from start to finish. Whether you're looking for [mention some of your key product categories, e.g., clothing, accessories, home decor, etc.], we hope you'll find something you love.\n" +
                "\n" +
                "Contact Us\n" +
                "\n" +
                "Have a question or need assistance? Feel free to contact us at [Your Contact Information]. We're here to help!\n" +
                "\n" +
                "Connect With Us\n" +
                "\n" +
                "Follow us on [list your social media platforms, e.g., Facebook, Instagram, Twitter, etc.] for the latest updates, promotions, and more.\n" +
                "\n" +
                "Thank you for visiting [Your Online Shop Name]. We look forward to serving you!" ,R.drawable.iconamoon_arrow_down_thin , R.drawable.help),

        Setting(5, "Contact","Contact Us\n" +
                "\n" +
                "Thank you for visiting [U-Store]! We are here to provide you with the best possible shopping experience. If you have any questions, concerns, or feedback, please don't hesitate to reach out. Our team is dedicated to ensuring your satisfaction, and we look forward to hearing from you.\n" +
                "\n" +
                "How Can We Help?\n" +
                "\n" +
                "Whether you need help with an order, have questions about our products, or require assistance navigating our site, our team is ready to assist you with any inquiries.\n" +
                "\n" +
                "Contact Options:\n" +
                "\n" +
                "Email Us:\n" +
                "Send your queries to [amirrllotfi@gmail.com] and we will get back to you within 24 hours.\n" +
                "\n" +
                "Call Us:\n" +
                "Speak directly with a customer service representative by calling [0049 42122222-21]. Our phone lines are open from [8:00 -19:00].\n" +
                "\n" +
                "Live Chat:\n" +
                "For immediate assistance, use our Live Chat feature on our website. Available from [8:00 -19:00].\n" +
                "\n" +
                "Fill Out Our Contact Form:\n" +
                "Visit our website and fill out the contact form [Link to Contact Form]. We respond to all inquiries within one business day.\n" +
                "\n" +
                "Mailing Address:\n" +
                "If you prefer to write to us, please send your letters to:\n" +
                "[Your Physical or Mailing Address]\n" +
                "\n" +
                "Follow Us:\n" +
                "\n" +
                "Stay connected with us through our social media channels to get the latest updates, deals, and more:\n" +
                "\n" +
                "[Facebook Page]\n" +
                "[Instagram Profile]\n" +
                "[Twitter Handle]\n" +
                "[Any Other Social Media Platform]\n" +
                "FAQ:\n" +
                "\n" +
                "Before reaching out, please visit our [FAQ Page Link] where we address common questions and concerns. This may save you time and provide immediate answers to your queries.\n" +
                "\n" +
                "Feedback:\n" +
                "\n" +
                "Your feedback is important to us. If you have suggestions on how we can improve, please let us know. We are committed to continuous improvement and value your insights.\n" +
                "\n" +
                "Visit Us:\n" +
                "\n" +
                "If you are in the area, come by and visit our store at:\n" +
                "[Your Store Address]\n" +
                "[Store Hours]\n" +
                "\n" +
                "We look forward to assisting you and hope you enjoy your shopping experience with us!",R.drawable.iconamoon_arrow_down_thin , R.drawable.mobile_usage)



    )


}

