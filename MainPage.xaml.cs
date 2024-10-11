namespace NoteTaker
{
    public partial class MainPage : ContentPage
    {
        public string Name;
        public MainPage()
        {
            InitializeComponent();
        }
        private void NewNote(object sender, EventArgs e)
        {
            EnterText.IsVisible = true;
            EnterText.Text = "";
            EnterText.Placeholder = "Enter Note Name";
            EnterText.Keyboard = Keyboard.Text;

        }
        private void EnterDone(object sender, EventArgs e)
        {
            Name = EnterText.Text;
            EnterText.IsVisible = false;
                BigEnter.IsVisible = true;
                BigEnter.IsReadOnly = false;
                BigEnter.Text = Preferences.Get(EnterText.Text.ToLower(),"");
                BigEnter.Placeholder = $"Enter Contents of {Name}";

        }
        private void BigEnterDone(object sender, EventArgs e)
        {
            try
            {
                Preferences.Set(Name.ToLower(), BigEnter.Text);
                BigEnter.IsReadOnly = true;
                BigEnter.IsVisible = false;
                BigEnter.Text = "";
                Anounce.Text = $"The note {Name} has been saved.";
            }
            catch {
                Anounce.Text = "Error";
            }
        }
        private void OpenNote(object sender, EventArgs e)
        {
            
            EnterText.IsVisible = true;
            EnterText.Text = "";
            EnterText.Placeholder = "Enter Note Name";
            EnterText.Keyboard = Keyboard.Text;
        }
    }


}
