using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;
using System.Xml;
using System.Xml.Linq;
using System.IO;
using System.Net;

namespace WindowsFormsApp1
{
    public partial class Form1 : Form
    {
        public Form1()
        {
            InitializeComponent();
        }

        private void Form1_Load(object sender, EventArgs e)
        {

        }
        //Declare a variable to store the city entered by the user
        string city;
        private void button1_Click(object sender, EventArgs e)
        {
            //get the cityname from the textbox
            city = txtcity.Text;

            //build the API request url with the specified city
            string uri = string.Format("http://api.weatherapi.com/v1/forecast.xml?key=572ea935fb0343319c4234607232807&q={0}&days=1&aqi=no&alerts=no", city);

            //load the xml response from the weather API
            XDocument doc = XDocument.Load(uri);

            //extraxt the weather icon URL from the XML response
            string iconUri = (string)doc.Descendants("icon").FirstOrDefault();

            //Dowload the weather icon image from the API
            WebClient client = new WebClient();
            byte[] image = client.DownloadData("http:" + iconUri);

            //convert the downloaded image data into a Bitmap
            MemoryStream stream = new MemoryStream(image);
            //Bitmap is getting the image stream
            Bitmap newBitmap = new Bitmap(stream);
            
            // temperatures from the XML document
            string maxtemp = (string)doc.Descendants("maxtemp_c").FirstOrDefault();
            string mintemp = (string)doc.Descendants("mintemp_c").FirstOrDefault();

            //wind speed

            string maxwindm = (string)doc.Descendants("maxwind_mph").FirstOrDefault();
            string maxwindk = (string)doc.Descendants("maxwind_kph").FirstOrDefault();

            //humidity
            string humidity = (string)doc.Descendants("avghumidity").FirstOrDefault();

            //country and cloud

            string country = (string)doc.Descendants("country").FirstOrDefault();

            string cloud = (string)doc.Descendants("text").FirstOrDefault();

            //set the image icon in the forms picture box
            Bitmap icon = newBitmap;

            txtmaxtemp.Text = maxtemp;
            txtmintemp.Text = mintemp;

            txtwindm.Text = maxwindm;
            txtwindk.Text = maxwindk;

            txthumidiy.Text = humidity;

            label7.Text = cloud;
            txtcountry.Text = country;

            //picture box icon
            pictureBox1.Image = icon;

           





        }

      



        private void button2_Click(object sender, EventArgs e)
        {
            // Create a DataTable to store the weather forecast data for multiple days
            DataTable dt = new DataTable();
            dt.Columns.Add("country", typeof(string));
            dt.Columns.Add("Date", typeof(string));
            dt.Columns.Add("Max Temp", typeof(string));
            dt.Columns.Add("Min Temp", typeof(string));
            dt.Columns.Add("Maxwindmph", typeof(string));
            dt.Columns.Add("MaxWindkph", typeof(string));
            dt.Columns.Add("Humidity", typeof(string));
            dt.Columns.Add("Cloud", typeof(string));
            dt.Columns.Add("Icon", typeof(string));


            city = txtcity.Text;

            //load the api
            string uri = string.Format("http://api.weatherapi.com/v1/forecast.xml?key=572ea935fb0343319c4234607232807&q={0}&days=7&aqi=no&alerts=no", city);
            //load the xml file associated with the api
            XDocument doc = XDocument.Load(uri);

            //do same thing like button click 1 but in a loop for 7 days and display the same information but 7 times
            foreach(var npc in doc.Descendants("forecastday"))
            {
                string iconUri = (string)npc.Descendants("icon").FirstOrDefault();

                WebClient client = new WebClient();

                byte[] image = client.DownloadData("http:" + iconUri);

                MemoryStream stream = new MemoryStream(image);


                Bitmap newBitmap = new Bitmap(stream);
                

                dt.Rows.Add(new object[]
                {
                    //loop for 7 days
                    (string)doc.Descendants("country").FirstOrDefault(),
                    (string)npc.Descendants("date").FirstOrDefault(),
                    (string)npc.Descendants("maxtemp_c").FirstOrDefault(),
                    (string)npc.Descendants("mintemp_c").FirstOrDefault(),
                    (string)npc.Descendants("maxwind_mph").FirstOrDefault(),
                    (string)npc.Descendants("maxwind_kph").FirstOrDefault(),
                    (string)npc.Descendants("avghumidity").FirstOrDefault(),
                    (string)npc.Descendants("text").FirstOrDefault(),
                    newBitmap





                });
            }
            //after loop

            dataGridView1.DataSource = dt;//put the info into datagrid view



        }

        
    }
}
