const routes = [
  {
    name: "Calmer Route",
    level: "Low",
    description: "Lower pedestrian density and close to public open spaces."
  },
  {
    name: "Direct Route",
    level: "High",
    description: "Faster, but passes through busier CBD pedestrian corridors."
  }
];

const refuges = ["State Library Victoria", "Flagstaff Gardens", "Carlton Gardens"];

export default function App() {
  return (
    <main className="app-shell">
      <section className="hero">
        <p className="eyebrow">FIT5120 Onboarding MVP</p>
        <h1>Sensory-Aware Navigation for Melbourne CBD</h1>
        <p className="intro">
          Compare walking routes by sensory load, crowd density and nearby quiet
          spaces.
        </p>

        <form className="search-panel">
          <label htmlFor="destination">Destination</label>
          <div className="search-row">
            <input
              id="destination"
              type="text"
              placeholder="e.g. Melbourne Central"
              defaultValue="Melbourne Central"
            />
            <button type="button">Find Routes</button>
          </div>
        </form>
      </section>

      <section className="content-grid" aria-label="Route results">
        <div className="map-placeholder">
          <span>Map area</span>
        </div>

        <div className="results-panel">
          <h2>Suggested Routes</h2>
          {routes.map((route) => (
            <article className="route-card" key={route.name}>
              <div>
                <h3>{route.name}</h3>
                <p>{route.description}</p>
              </div>
              <strong className={`level level-${route.level.toLowerCase()}`}>
                {route.level}
              </strong>
            </article>
          ))}

          <h2>Nearby Quiet Spaces</h2>
          <ul className="refuge-list">
            {refuges.map((place) => (
              <li key={place}>{place}</li>
            ))}
          </ul>
        </div>
      </section>
    </main>
  );
}
