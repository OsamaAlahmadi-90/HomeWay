<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>HomeWay README</title>
</head>
<body>

<h1 id="top">HomeWay – Property Services Management Platform</h1>

<p>
  <strong>HomeWay</strong> is a Spring Boot backend that connects customers with specialized service companies to manage residential properties efficiently.
  It supports end-to-end service lifecycles for <strong>Inspection</strong>, <strong>Maintenance</strong>, <strong>Moving</strong>, and <strong>Redesign</strong>,
  enforcing structured workflows, secure access control, transparent pricing, real-time notifications, payment processing, and AI-powered assistance.
</p>

<hr />

<h2>Table of Contents</h2>
<ul>
  <li><a href="#introduction">1. Introduction</a></li>
  <li><a href="#key-features">2. Key Features</a></li>
  <li><a href="#roles-permissions">3. User Roles &amp; Permissions</a></li>
  <li><a href="#architecture">4. Architecture</a></li>
  <li><a href="#core-workflows">5. Core Workflows</a></li>
  <li><a href="#problems-solved">6. Problems Solved</a></li>
  <li><a href="#contributing">7. Contributing</a></li>
  <li><a href="#contributors">8. Contributors</a></li>
</ul>

<hr />

<h2 id="introduction">1. Introduction</h2>
<p>
  HomeWay is designed as a realistic, production-style backend system. It integrates business rules, role-based authorization,
  payment verification, notifications, reporting, and subscription-gated AI features to enhance decision-making for customers
  and service providers.
</p>
<p>
  The platform centers around a controlled request lifecycle:
  <strong>Pending → Approved → In Progress → Completed</strong> (or <strong>Rejected</strong>),
  with strict validation of role permissions, ownership, and state transitions.
</p>
<p><a href="#top">↑ Back to top</a></p>

<hr />

<h2 id="key-features">2. Key Features</h2>

<h3>Property Management</h3>
<ul>
  <li>Customers can create, update, and manage multiple properties.</li>
  <li>All service requests are tied to a specific property for traceability.</li>
</ul>

<h3>Service Request Lifecycle</h3>
<ul>
  <li>Controlled flow: <strong>Pending → Approved → In Progress → Completed</strong> (or <strong>Rejected</strong>).</li>
  <li>Requests are explicitly linked to a company and a property.</li>
  <li>Strict validation of request state transitions and ownership checks.</li>
</ul>

<h3>Offer &amp; Payment System</h3>
<ul>
  <li>Companies approve requests by creating a pricing offer.</li>
  <li>Customers can accept or reject offers before payment.</li>
  <li>Secure payment processing via <strong>Moyasar</strong> with verification and automatic request payment updates.</li>
</ul>

<h3>Resource Management</h3>
<ul>
  <li>Automatic assignment of available workers for requests.</li>
  <li>Vehicle assignment for moving services.</li>
  <li>Availability tracking to prevent double-booking and conflicts.</li>
</ul>

<h3>Reports &amp; Reviews</h3>
<ul>
  <li>Workers generate structured reports after request completion.</li>
  <li>Customers can view reports for their own requests.</li>
  <li>Customers can submit reviews (one per completed request) to support service transparency.</li>
</ul>

<h3>Notification System</h3>
<ul>
  <li>Notifications created for critical events (approval, rejection, start, completion, review creation, etc.).</li>
  <li>Supports customer, company, and worker notification flows.</li>
</ul>

<h3>AI-Powered Assistance (Subscription Based)</h3>
<ul>
  <li>Cost estimation and breakdowns (customer/company perspective).</li>
  <li>Timeline estimation and planning guidance.</li>
  <li>Issue diagnosis (text &amp; image URL input).</li>
  <li>Inspection planning checklists and prioritization.</li>
  <li>Worker safety requirements and repair checklists.</li>
  <li>Redesign scope &amp; style suggestions.</li>
</ul>

<h3>Subscription &amp; Billing</h3>
<ul>
  <li><strong>FREE</strong> and <strong>AI</strong> plans.</li>
  <li>AI features available only for active subscriptions.</li>
  <li>Scheduled email reminders for renewal and expiry.</li>
</ul>

<p><a href="#top">↑ Back to top</a></p>

<hr />

<h2 id="roles-permissions">3. User Roles &amp; Permissions</h2>

<h3>Customer</h3>
<ul>
  <li>Manage profile and properties.</li>
  <li>Create service requests (inspection, moving, maintenance, redesign).</li>
  <li>View/manage own requests and offers.</li>
  <li>Accept/reject offers and pay for services.</li>
  <li>View reports and submit reviews after completion.</li>
  <li>Use AI features if subscribed.</li>
</ul>

<h3>Company (Role-specialized)</h3>
<p>
  Company accounts are specialized by role:
  <strong>INSPECTION_COMPANY</strong>, <strong>MOVING_COMPANY</strong>, <strong>MAINTENANCE_COMPANY</strong>, <strong>REDESIGN_COMPANY</strong>.
</p>
<ul>
  <li>Requires admin approval to operate.</li>
  <li>View assigned requests.</li>
  <li>Approve/reject pending requests and create pricing offers.</li>
  <li>Start requests only after validating: offer accepted + request paid + correct state.</li>
  <li>Assign/release resources (workers; vehicles for moving).</li>
  <li>View reviews and notifications related to company operations.</li>
</ul>

<h3>Worker</h3>
<ul>
  <li>Works under a company and is assigned to requests.</li>
  <li>Can access only requests assigned to them.</li>
  <li>Create/update/delete reports (after request completion).</li>
  <li>AI tools available only if: subscription is active + worker account is active.</li>
</ul>

<h3>Admin</h3>
<ul>
  <li>Approve/reject company registrations.</li>
  <li>Oversee platform data, users, and notifications.</li>
  <li>Manage platform-level operations and governance flows.</li>
</ul>

<p><a href="#top">↑ Back to top</a></p>

<hr />

<h2 id="architecture">4. Architecture</h2>
<p>
  HomeWay follows a layered Spring Boot architecture to keep responsibilities clean and scalable:
</p>

<h3>Controller Layer</h3>
<ul>
  <li>RESTful APIs for customers, companies, workers, and admin.</li>
  <li>Authentication via Spring Security (Basic Auth) and <code>@AuthenticationPrincipal</code>.</li>
</ul>

<h3>Service Layer</h3>
<ul>
  <li>Business logic + workflow enforcement (state transitions, ownership checks, role gating).</li>
  <li>Transactional operations for request lifecycle, resource assignment, and payment confirmation.</li>
  <li>Subscription checks that gate AI features.</li>
</ul>

<h3>Persistence Layer</h3>
<ul>
  <li>JPA/Hibernate relational mapping.</li>
  <li>Strong entity ownership via <code>@OneToOne</code>, <code>@ManyToOne</code>, and <code>@MapsId</code>.</li>
</ul>

<h3>External Integrations</h3>
<ul>
  <li><strong>Moyasar</strong> for payments (offers + subscription billing).</li>
  <li><strong>OpenAI API</strong> for AI assistance endpoints.</li>
  <li>Email service for renewal/expiry notifications.</li>
</ul>

<p><a href="#top">↑ Back to top</a></p>

<hr />

<h2 id="core-workflows">5. Core Workflows</h2>

<h3>Service Request Flow</h3>
<ol>
  <li>Customer selects a property and a company, then creates a request (<strong>Pending</strong>).</li>
  <li>Company reviews the request:
    <ul>
      <li><strong>Approve</strong>: creates an offer with a price → request becomes <strong>Approved</strong>.</li>
      <li><strong>Reject</strong>: request becomes <strong>Rejected</strong>.</li>
    </ul>
  </li>
  <li>Customer accepts the offer and completes payment (gates service execution).</li>
  <li>Company starts the request:
    <ul>
      <li>Validates offer is accepted + request is paid + correct status.</li>
      <li>Assigns an available worker (and a vehicle for moving requests).</li>
      <li>Updates availability and marks request <strong>In Progress</strong>.</li>
    </ul>
  </li>
  <li>Company completes the request:
    <ul>
      <li>Releases worker/vehicle resources and restores availability.</li>
      <li>Marks request <strong>Completed</strong> and stores completion dates.</li>
    </ul>
  </li>
  <li>Notifications are created at key steps for customer/company/worker visibility.</li>
</ol>

<h3>Payment Flow</h3>
<ol>
  <li>Customer pays an accepted offer via Moyasar.</li>
  <li>System verifies payment status using Moyasar APIs.</li>
  <li>Request <code>isPaid</code> is updated automatically upon successful verification.</li>
  <li>Request execution (start/complete) is blocked unless paid.</li>
</ol>

<h3>AI Feature Flow</h3>
<ol>
  <li>User subscribes to AI plan (subscription stored and payment tracked).</li>
  <li>Every AI endpoint validates subscription status before responding.</li>
  <li>Additional gating rules apply depending on endpoint:
    <ul>
      <li>Worker must be active for worker tools.</li>
      <li>Company role must match feature type (e.g., moving tools for moving company).</li>
    </ul>
  </li>
  <li>AI responses are returned in structured, actionable formats.</li>
</ol>

<p><a href="#top">↑ Back to top</a></p>

<hr />

<h2 id="problems-solved">6. Problems Solved</h2>
<ul>
  <li><strong>Unstructured service requests:</strong> Enforces a controlled lifecycle and valid state transitions.</li>
  <li><strong>Pricing ambiguity:</strong> Uses explicit offers + acceptance before payment.</li>
  <li><strong>Resource conflicts:</strong> Prevents double-assignment of workers/vehicles with availability tracking.</li>
  <li><strong>Unauthorized access:</strong> Strong role-based and ownership validation for sensitive operations.</li>
  <li><strong>Poor communication:</strong> Built-in notifications for all critical service events.</li>
  <li><strong>Lack of decision support:</strong> AI tools assist planning, estimation, diagnosis, and documentation.</li>
  <li><strong>Scalability:</strong> Modular structure supports adding service types and AI tools without redesign.</li>
</ul>

<p><a href="#top">↑ Back to top</a></p>

<hr />

<h2 id="contributing">7. Contributing</h2>
<p>
  This project was developed as a collaborative capstone project. The current version represents a complete implementation 
  of the HomeWay platform with all planned features and workflows.
</p>
<p>
  While this repository showcases our final work, we welcome feedback, suggestions, and discussions about the 
  implementation. If you're interested in learning more about specific features or have questions about our approach:
</p>
<ul>
  <li>Open an issue to discuss potential improvements or ask questions about the architecture.</li>
  <li>Review our comprehensive documentation to understand the design decisions and workflows.</li>
  <li>Check out the Postman documentation and test cases to explore the API endpoints.</li>
</ul>
<p>
  If you'd like to build upon this work or adapt it for your own use case, feel free to fork the repository. 
  Please ensure you maintain the existing structure (Controller → Service → Repository) and preserve validation 
  and authorization rules when making modifications.
</p>

<p><a href="#top">↑ Back to top</a></p>

<hr />

<h2 id="contributors">8. Contributors</h2>
<ul>
  <li>
    <a href="https://github.com/Turki1927">@Turki1927</a> —
    Property, Worker, Admin, Notifications, SubscriptionPaymentService, UserSubscriptionService. 
    Request (Maintenance Flow). Request (Redesign Flow). JUnit Test (Service). Postman Testing (Local/deployment testing). 
    Figma Initial Design. Class Diagram. 
    External APIs: Subscription payment (Moyasar payment), Email Integration (Subscription/Notifications), 
    AI endpoints (customerServicesTimeEstimation, customerReviewWritingAssist, workerRepairChecklist, 
    workerSafetyRequirements, companyServiceEstimationCost, maintenanceCompanySparePartsCosts).
  </li>
  <li>
    <a href="https://github.com/leenref">@leenref</a> —
    Report, Vehicle, Customer, RequestPaymentService. Request (Moving Flow). JUnit Test (Controller). 
    Postman Testing (Local/deployment testing). Deployment. Postman Documentation. Figma Initial Design. Class Diagram. 
    External APIs: Service payment (Moyasar payment), AI endpoints (customerAskAIWhatServiceDoesTheIssueFits, 
    customerIsFixOrDesignCheaper, workerReportCreationAssistant, companyInspectionPlanningAssistant, 
    movingCompanyTimeAdvice, maintenanceFixOrReplace).
  </li>
  <li>
    <a href="https://github.com/OsamaAlahmadi-90">@OsamaAlahmadi-90</a> —
    User, Offer, UserRegister, Review, Company. Request (Inspection Flow). JUnit Test (Repository). 
    Postman Testing (Local/deployment testing). Figma Final Design. Class Diagram. Use Case diagram. Presentation. 
    External APIs: Email integration (Company/Customer Requests), AI endpoints (customerRequestCostEstimation, 
    customerReportSummary, workerIssueDiagnosis, movingCompanyResourceMovingEstimation, workerJobTimeEstimation, 
    maintenanceCompanyMaintenancePlan, redesignCompanyRedesignScope, customerRedesignFromImage, companyIssueImageDiagnosis).
  </li>
</ul>

<p><a href="#top">↑ Back to top</a></p>

</body>
</html>
