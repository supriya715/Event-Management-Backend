@RestController
@RequestMapping("/admin")
@CrossOrigin("*")
public class AdminController 
{
  @Autowired
  private AdminService adminService;

  @Autowired
  private EMailService mailService;

  @PostMapping("/checkadminlogin")
  public ResponseEntity<?> checkadminlogin(@RequestBody Admin admin)
  {
      try 
      {
          Admin a = adminService.checkadminlogin(admin.getUsername(), admin.getPassword());

          if (a!=null) 
          {
              return ResponseEntity.ok(a);
          } 
          else 
          {
              return ResponseEntity.status(401).body("Invalid Username or Password");
          }
      } 
      catch (Exception e) 
      {
          return ResponseEntity.status(500).body("Login failed: " + e.getMessage());
      }
  }
  @GetMapping("/testmail")
public String testMail() {
    mailService.sendEmail(
        "minikamatma123@gmail.com", // must be verified
        "Test Email from AWS SES",
        "Your email service is working successfully 🚀"
    );
    return "Mail Sent Successfully!";
}

  @GetMapping("/viewallcustomers")
  public ResponseEntity<List<Customer>> viewallcustomers()
  {
      return ResponseEntity.ok(adminService.displaycustomers());
  }

  @PostMapping("/addeventmanager")
  public ResponseEntity<String> addeventmanager(@RequestBody Manager manager)
  {
     try
     {
        String output = adminService.addeventmanager(manager);

        // ✅ Email to manager
        mailService.sendEmail(
            manager.getEmail(),
            "Welcome to Event Management System",
            "Hello " + manager.getName() + ", your account has been created."
        );

        return ResponseEntity.ok(output);
     }
     catch(Exception e)
     {
         return ResponseEntity.status(500).body("Failed to Add Event Manager ... !!"); 
     }
  }

  @DeleteMapping("/deletecustomer")
  public ResponseEntity<String> deletecustomer(@RequestParam int cid)
  {
    try
     {
        String output = adminService.deletecustomer(cid);

        mailService.sendEmail(
            "admin@gmail.com",
            "Customer Deleted",
            "Customer with ID " + cid + " has been deleted."
        );

        return ResponseEntity.ok(output);
     }
     catch(Exception e)
     {
          return ResponseEntity.status(500).body("Failed to Delete Customer ... !!"); 
     }
  }

  @DeleteMapping("/deletemanager")
  public ResponseEntity<String> deletemanager(@RequestParam int mid)
  {
    try
     {
        String output = adminService.deletemanager(mid);

        mailService.sendEmail(
            "admin@gmail.com",
            "Manager Deleted",
            "Manager with ID " + mid + " has been removed."
        );

        return ResponseEntity.ok(output);
     }
     catch(Exception e)
     {
          return ResponseEntity.status(500).body("Failed to Delete Manager ... !!"); 
     }
  }

  @GetMapping("/viewalleventmanagers")
  public ResponseEntity<List<Manager>> viewalleventmanagers()
  {
      return ResponseEntity.ok(adminService.displayeventmanagers());
  }

  @GetMapping("/status")
  public String status() 
  {
    return "Event Management Backend running successfully on AWS Elastic Beanstalk";
  }

  @GetMapping("/customercount")
  public ResponseEntity<Long> getCustomerCount()
  {
      return ResponseEntity.ok(adminService.displaycustomercount());
  }

  @GetMapping("/managercount")
  public ResponseEntity<Long> getManagerCount()
  {
      return ResponseEntity.ok(adminService.displaymanagercount());
  }

  @GetMapping("/eventcount")
  public ResponseEntity<Long> getEventCount()
  {
      return ResponseEntity.ok(adminService.displayeventcount());
  }
}
